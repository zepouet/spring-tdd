# Récupérer un joueur

## Définition d'un joueur

Un joueur est défini ainsi : 
- Il a un email unique (clef primaire)
- Il un nom composé (Nom + Prénom) 
- Il a un type CASUAL ou REGULAR
Aucun des champs n'est optionnel

Ecrire les tests permettant de valider l'unicité d'un joueur et les contraintes.

## Niveau intégration

Au niveau de **src/tests/java/fr.treeptik.training.player.PlayersIntegrationIT**

Ecrire un test pour une API en **/players/{email}** qui valide la récupération d'une personne sur base de son email
* Le nom de la Personne (prénom + nom)
* Le type de joueur (Casual ou Regular)

Exemple : si on récupére le joueur sur base de son email "n.muller@treeptik.fr",
il faut que son nom soit "Nicolas Muller et que son type soit "casual".

## Niveau Controller

Au niveau de **src/tests/java/fr.treeptik.training.player.PlayersControllerTests**

Ecrire une classe de test pour le Controller **fr.treeptik.training.controller.PlayersController** respectant :
* Il faut mocker le service **PlayerService** qui va retourner un utilisateur
* Si on récupére le joueur sur base de son email "n.muller@treeptik.fr",
  il faut que son nom soit "Nicolas Muller et que son type soit "casual".
* Si on cherche à récupérer un joueur inexistant avec un email "nausicaa.muller@gmail.com"
  il faut retourner un code 404 Not Found
  
Tips : Pensez à utiliser @WebMvcTest et @MockBean
  
## Niveau Service

Au niveau de **src/tests/java/fr.treeptik.training.player.PlayerServiceTests**

Ecrire une classe de test pour le Service **fr.treeptik.training.service.PlayerServiceTests** respectant :
* Il faut mocker le service **PlayerService** qui va retourner un utilisateur
* Si on récupére le joueur sur base de son email "n.muller@treeptik.fr",
  il faut que son nom soit "Nicolas Muller et que son type soit "casual".
* Si on cherche à récupérer un joueur inexistant avec un email "nausicaa.muller@gmail.com"
  il faut retourner un code 404 Not Found

## Niveau Repository

Au niveau de **src/tests/java/fr.treeptik.training.domain.PlayerRepositoryTest**

Ecrire une classe de test pour le Service **fr.treeptik.training.domain.PlayerRepository** respectant en utilisant 
TestEntityManager

## Ajouter une nouvelle Entité "Grille"

Rajouter une route sur le Controller Joueur selon `players/{email}/play`
Une Grille se compose de 3 chiffres compris entre 1 et 50 sans doublon.

Pour cela vous devez ajouter des tests de cohérences sur l'objet grille.
L'objet passée dans le Body de la Request doit être validée à l'entrée du controller.
Persister l'information et dans le cas où elle est gagnante retourner le bon code HTTP 
ainsi que la nouvelle URI pour accéder au gain.

Le gain s'obtient avec un `Get` sur la route `/grille/{id}/gain`.
Le résultat du gain est évidemment un entier mais aussi la décomposition en pièces.

Pour réaliser ceci, nous allons mocker avec WireMock la partie monnayer


