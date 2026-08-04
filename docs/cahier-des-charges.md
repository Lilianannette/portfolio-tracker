# Cahier des charges — Portfolio Tracker

Ce document décrit *ce que le système fait* (le métier), indépendamment de toute implémentation technique.

---

## 1. Le domaine en une phrase

Aider l'investisseur à suivre son portefeuille et son évolution, et à savoir réellement ce qu'il a gagné en tenant compte des différents frais.

---

## 2. Vocabulaire du domaine

| Terme | Définition |
|---|---|
| Transaction | Un achat ou une vente, à une date, d'une quantité à un prix. |
| Stock | Une action cotée, avec ses paramètres (nom, ticker, cours). |
| Portfolio | Le portefeuille, qui réunit l'ensemble des positions. |
| Money / Amount | Un montant monétaire. |
| Fee | Un frais : de courtage (rattaché à une transaction) ou d'abonnement (non rattachable). |
| Holding | La position détenue sur un stock donné (agrégat des transactions). |
| Dividend | Argent versé au titre de la possession d'une action. |

---

## 3. Règles métier — Lot 1

### R1 (A) — Prix de revient d'une position

**Définition :** combien m'a coûté, en moyenne pondérée, une action que je détiens, frais de courtage inclus.
Formule : (total payé pour les achats + frais de courtage de ces achats) ÷ nombre d'actions détenues.

**Décision :** j'inclus les frais de courtage dans le prix de revient, parce qu'ils font partie de ce que l'action m'a réellement coûté à l'achat. Les frais qui ne concernent pas un achat précis (versement, abonnement) relèvent de la règle R4.

### R2 (B) — Valorisation d'une position

**Définition :** combien vaut une position aujourd'hui = quantité détenue × cours actuel.

**Décision :** calcul direct, une seule réponse possible. Cette règle existe parce que R3 en a besoin.

### R3 (C) — Plus-value réalisée vs latente

**Définition :** combien j'ai gagné, en distinguant ce qui est vendu (réalisé, définitif) de ce qui est encore en position (latent, peut évoluer).

**Décision :** j'affiche les deux séparément. Le gain latent parce que l'investisseur veut voir où en est sa position même sans vendre (c'est l'information la plus regardée). Le gain réalisé parce que c'est le seul qui est définitif. Je ne les additionne pas dans un seul chiffre : ce sont deux natures différentes (une valeur *dans* le portefeuille vs une valeur *sortie* du portefeuille).

### R4 (G) — Frais non rattachables

**Définition :** traiter les frais qui ne concernent aucune position précise (abonnement courtier, droits de garde), par opposition aux frais de courtage rattachés à un achat.

**Décision :** les frais non rattachables sont portés par le portefeuille global, pas répartis sur les positions. Parce qu'ils n'appartiennent à aucune action en particulier, et que les répartir au prorata ajoute une complexité inutile au démarrage. La performance d'une position reste « pure » (ses achats + ses frais de courtage) ; les frais généraux s'appliquent au niveau du portefeuille.

---

## 4. Règles métier — Lot 2 (réserve)

- **F — Méthode de calcul FIFO vs prix moyen** — la future Strategy, le test d'OCP.
- **D — Traitement d'un dividende** — quand une action me verse de l'argent, où je le compte ? (plusieurs choix possibles, à trancher au T2)
- **E — Split d'action** — l'événement rétroactif (recalcul du prix de revient).
- **H — Performance globale du portefeuille** — pondérée par le temps vs par les flux.
- **I — Performance brute vs nette** — afficher une performance sans les frais généraux et une avec.

---

## 5. Dépendances externes

| Point de contact | Départ (T1) | Bascule (T3) |
|---|---|---|
| Source des cours | Fichier JSON local | Une API boursière (puis une autre) |
| Source des transactions | Fichier JSON local | Base PostgreSQL (JPA) |
| Sortie (rapport) | Console | Fichier Markdown, puis email |
| L'horloge (« aujourd'hui ») | À déterminer | — |

---

## 6. Hors périmètre

- Pas d'authentification ni de multi-utilisateur.
- Pas d'interface graphique ni d'application mobile.
- Pas de graphiques, dashboards, export PDF.
- Pas de fiscalité (PEA/CTO, impôts).
- Pas de déploiement, Docker.
- Pas de module budget.

---

## 7. Données de test

- Une position achetée mais jamais vendue.
- Deux achats de la même action à des prix différents (teste R1).
- Une vente partielle d'une position.
- Une action qui verse un dividende (en réserve).
- Un frais d'abonnement un mois sans aucune transaction (teste R4).
- Un portefeuille vide.

---

## 8. Comportements attendus

**Exemple 1 (prix de revient, frais inclus — R1) :**
- Étant donné 10 actions achetées à 100 € avec 5 € de frais de courtage, quand je calcule le prix de revient unitaire, alors il vaut 100,50 €.

**Exemple 2 (prix de revient moyen pondéré — R1) :**
- Étant donné 10 actions à 100 € puis 30 actions à 200 €, quand je calcule le prix de revient moyen, alors il vaut 175 €.

**Exemple 3 (réalisé vs latent — R3) :**
- Étant donné 30 actions achetées à 75 €, dont 10 revendues à 140 €, les 20 restantes cotant 95 € aujourd'hui, quand je calcule mes plus-values, alors le gain réalisé est de 650 € et le gain latent est de 400 €.

**Exemple 4 (frais non rattachables — R4) :**
- Étant donné une position de 20 actions cotant 100 € et un frais d'abonnement de 5 € dans le mois, quand je regarde la performance de la position puis celle du portefeuille, alors la performance de la position ignore les 5 € et le frais apparaît uniquement au niveau du portefeuille global.

**Exemple 5 (cas limite — portefeuille vide) :**
- Étant donné un portefeuille sans aucune transaction, quand je calcule sa valeur et ses plus-values, alors tout vaut 0 € et l'application ne plante pas.