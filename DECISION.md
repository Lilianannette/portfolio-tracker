# Journal de décisions — Portfolio Tracker

## D001 — Frais de courtage inclus dans le prix de revient
- Contexte : une action achetée entraîne une commission de courtage.
- Options : (a) inclure les frais dans le coût, (b) les compter à part.
- Choix : inclus dans le prix de revient.
- Raison : ils font partie de ce que l'action m'a réellement coûté à l'achat.
- Coût accepté : le prix de revient n'est pas un "prix de marché pur".

## D002 — Frais non rattachables portés par le portefeuille global
- Contexte : abonnement, droits de garde ne concernent aucune action précise.
- Options : (a) répartir au prorata, (b) porter au niveau portefeuille, (c) perf brute vs nette.
- Choix : option (b), niveau portefeuille.
- Raison : ils n'appartiennent à aucune position ; le prorata ajoute de la complexité inutile au lot 1.
- Coût accepté : la performance d'une position seule ne "voit" pas ces frais. (Option c reportée en réserve.)

## D003 — Plus-value réalisée ET latente, affichées séparément
- Contexte : un gain peut être encaissé (vendu) ou seulement sur le papier (détenu).
- Options : (a) réalisé seul, (b) latent seul, (c) les deux séparés, (d) les deux additionnés.
- Choix : option (c), les deux distincts.
- Raison : le latent est l'info la plus regardée ; le réalisé est le seul définitif. Ce sont deux natures différentes.
- Coût accepté : deux chiffres à calculer et à présenter au lieu d'un.

## D004 — Une vente partielle ne change pas le prix de revient unitaire
- Contexte : je vends une partie d'une position achetée en plusieurs fois.
- Choix : en moyenne pondérée, le prix de revient unitaire des actions restantes est inchangé ; seule la quantité baisse.
- Raison : le prix de revient est une propriété des achats, pas des ventes.
- Coût accepté : cette simplicité n'est vraie qu'en moyenne pondérée ; en FIFO (réserve, règle F) ce serait différent — à revoir au T2.

## D005 — Cours depuis un fichier JSON local (pas une API) au démarrage
- Contexte : les cours changent en permanence ; une API serait "plus réaliste".
- Choix : fichier JSON local en T1, API repoussée au T3.
- Raison : (1) une API parasite le T1 avec du réseau/auth sans rapport avec l'OOP ; (2) un cours figé rend les tests possibles ; (3) le passage JSON→API EST le test de rupture du T3.
- Coût accepté : les cours ne sont pas temps réel pendant toute la phase d'apprentissage.

## D006 — Cucumber écarté pour l'instant
- Contexte : j'ai croisé Cucumber au travail, tenté de l'ajouter.
- Choix : tests JUnit "à la main" en Given/When/Then (commentaires), pas de Cucumber.
- Raison : Cucumber sert la communication avec des non-devs ; en solo il masque la friction de conception que je dois ressentir.
- Coût accepté : je n'apprends pas l'outil maintenant (noté : à explorer quand JUnit sera solide).

## D007 — Calculs dans le service, pas dans les objets
- Choix : tout le calcul (prix de revient, valorisation…) va dans PortfolioService ; Position et Transaction restent de simples porteurs de données.
- Raison : démarrage rapide, code volontairement "anémique" pour le refactorer ensuite.
- Coût accepté : modèle anémique assumé ; à réinterroger au refactoring (le prix de revient pourrait remonter dans Position).