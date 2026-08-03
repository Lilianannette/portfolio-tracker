# Cahier des charges — mon portefeuille boursier

> Rempli par : Lilian · Date : [...] Règle d'or : ce document décrit _ce que le système fait_ (le métier). **Aucun** nom de classe, package ou interface. Si ta main écrit `PortfolioService`, tu es sorti du cahier. Objectif : le remplir à ~80 %, honnête sur les trous. Pas la perfection.

---

## 1. Le domaine en une phrase

> À qui ça sert et pour faire quoi. Une seule phrase.

Aide l'investisseur à suivre son portefeuille et son évolution et de savoir réellement ce qu'il à gagner en prenant en compte différents frais.

---

## 2. Vocabulaire du domaine

> Nomme tes objets métier. En anglais (recommandé), une bonne fois, et tu t'y tiens.

| Terme          | Définition                                                        |
| -------------- | ----------------------------------------------------------------- |
| Transaction    | un achat ou une vente, à une date, d'une quantité à un prix       |
| Stock          | est une actions avec un multitude de paramètre (ex: name, price ) |
| Portfolio      | C'est le portefeuille qui réunion toute les action                |
| Money / Amount |                                                                   |
| Fee            | Frais courtage / Frais abonnement                                 |
| Holding        | Ma position sur un stocks définis                                 |
| dividends      | Argent versé pour la possessions d'action                         |

---

## 3. Règles métier — LOT 1 (ce que tu implémentes au T1)

> Pour chaque règle : la définition en langage naturel + **la décision que tu as prise** (le point où plusieurs réponses étaient possibles). La ligne « décision » est la plus importante — c'est elle qui prouve que c'est du domaine.

### R1 (A) — Prix de revient d'une ligne

**Définition :** combien m'a coûté, en moyenne, une action que je détiens.

> Contrainte de départ : **prix moyen pondéré uniquement** (FIFO est en réserve). Les frais d'achat : à toi de décider s'ils entrent dans le coût ou pas.

Ta définition précise : je met les coûts d'achat **Décision prise (et pourquoi) :** Car c'est frais de courtage sont les 1er rencontré et impacté directement la somme ex je d'acheter 100 d'une action et le courtier me prends 2€ de frais sur l'action

### R2 (B) — Valorisation d'une position

**Définition :** combien vaut ma ligne maintenant (quantité × cours actuel).

> Règle simple, une seule réponse possible. Elle est là parce que R3 en a besoin — ne cherche pas à la compliquer.

Ta définition précise : Savoir aujourd'hui combien vaut telle action dans mon portefeuille  **Décision prise (et pourquoi) :** quantité x cours actuel

### R3 (C) — Plus-value réalisée vs latente

**Définition :** combien j'ai gagné, en distinguant ce qui est **vendu** (réalisé, définitif) de ce qui est **encore en position** (latent, peut disparaître).

Ta définition précise : Savoir combien j'ai gagné ce qui signifie ce que j'ai vendu tant que la somme est sur le portefeuille il n'y a rien de gagné  **Décision prise (et pourquoi) :** j'affiche les deux séparément. Le gain latent parce que l'investisseur veut voir où en est sa position même sans vendre (c'est l'info la plus regardée). Le gain réalisé parce que c'est le seul qui est définitif. Je ne les additionne pas dans un seul chiffre : ce sont deux natures différentes (une valeur _dans_ le portefeuille vs une valeur _sortie_ du portefeuille).

### R4 (G) — Frais non rattachables

**Définition :** traiter les frais qui ne concernent aucune ligne précise (abonnement courtier, droits de garde).

> LE point de décision : ils grèvent quoi ? Répartis au prorata ? Gardés au niveau du portefeuille global ? Perf brute vs nette ? Choisis, ne cherche pas la "bonne" réponse.

Ta définition précise : En plus des frais lié à l'achat d'actions ou gestion du compte certain sont lié mais pas directement au actions et ce sont de ces frais la que nous parlons **Décision prise (et pourquoi) :** option 2 choisis soit tous les frais non rattachables rattaché au portefeuille global

---

## 4. Règles métier — LOT 2 (réserve, PAS avant le T2)

> Ne les implémente pas. Ne les anticipe pas dans ta conception du lot 1. Elles serviront à tester ton OCP au T2.

- **F — Méthode de calcul FIFO vs prix moyen** → _ta future Strategy, le test d'OCP par excellence._
- **D — Traitement d'un dividende** → _quand une action me verse de l'argent, où je le compte ?_
- **E — Split d'action** → _l'événement rétroactif (recalcul du prix de revient)._
- **H — Performance globale du portefeuille** → _pondérée temps vs flux, trop complexe pour le lot 1.
- **I — Ajouter l'option 3 des frais** → _gain brute / gain net_

---

## 5. Dépendances externes

> Pour chaque point de contact : le plus simple au T1, l'alternative pour le test de rupture du T3.

| Point de contact            | Départ (T1)        | Bascule (T3)                                                                                              |
| --------------------------- | ------------------ | --------------------------------------------------------------------------------------------------------- |
| Source des cours            | fichier JSON local | `[ex : une API boursière, puis une autre]`                                                                |
| Source des transactions     | fichier JSON local | base PostgreSQL (JPA)                                                                                     |
| Sortie (rapport)            | Console            | Fichier Markdown, puis email                                                                              |
| L'horloge (« aujourd'hui ») | ???                | ← _piège : tu le découvriras en écrivant ton 1er test qui échoue. Ne cherche pas la solution maintenant._ |

---

## 6. Hors périmètre — explicitement

> Ce que tu t'interdis d'ajouter, même au mois 7. Chaque ligne ici = du temps gagné.

- pas d'authentification ni de multi-utilisateur
- pas d'interface graphique ni d'appli mobile
- pas de graphiques, dashboards, export PDF
- pas de fiscalité (PEA/CTO, impôts)
- pas de déploiement, Docker
- pas de module budget — réservé au T3 si vraiment

---

## 7. Données de test

> Ce que tu génères **avant** de coder les règles. Liste les cas tordus à inclure exprès.

- une position achetée mais jamais vendue
- deux achats de la même action à des prix différents (teste R1)
- une vente partielle d'une position (le cas piège de R1)
- une action qui verse un dividende _(en réserve, mais note-la)_
- un frais d'abonnement un mois sans aucune transaction (teste R4)
- un portefeuille vide

---

## 8. Premiers comportements attendus (= tes premiers tests-spécifications)

> 3 à 5 comportements en donné / quand / alors, en langage naturel. Aucun outil requis. C'est déjà du « test avant le code », en mots.

**Exemple 1 (prix de revient frais inclus— R1) :**

> Étant donné 10 actions achetées à 100 € avec 5 € de frais de courtage, quand je calcule le prix de revient unitaire, alors il vaut 100,50 €.

**Exemple 2 (réalisé vs latent — R3) :**

> Étant donné 30 actions achetées à 75 €, dont 10 revendues à 140 €, les 20 restantes cotant 95 € aujourd'hui, quand je calcule mes plus-values, alors le gain réalisé est de 650 € et le gain latent est de 400 €.

**Exemple 3 (frais non rattachables — R4) :**

> Étant donné une position de 20 actions cotant 100 € et un frais d'abonnement de 5 € dans le mois, quand je regarde la performance de la position puis celle du portefeuille, alors la performance de la position ignore les 5 € et le frais apparaît uniquement au niveau du portefeuille global.

**Exemple 4 :**

> Étant donné 10 actions à 100 € puis 30 actions à 200 €, quand je calcule le prix de revient moyen, alors il vaut 175 €.

**Exemple 5 :**

> Étant donné un portefeuille sans aucune transaction, quand je calcule sa valeur et ses plus-values, alors tout vaut 0 € et l'app ne plante pas.

