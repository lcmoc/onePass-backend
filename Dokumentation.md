# Dokumentation

## Backend
### Done:
- Endpoints
- Entities (User, Category, Credentials)
- Repositories (User, Category, Credentials)
- Services (User, Category, Credentials)
- Controller (User, Category, Credentials)

### Todo:
- Uuid check
- `@GetMapping` zu `@PostMapping` umändern

## Frontend
### Done
- Login
- Register
- Nutzer löschen
- Nutzer Passwort ändern
- Nutzer ausloggen
- Kategorien hinzufügen
- Kategorien löschen
- Kategorien bearbeiten
- Credentials hinzufügen
- Credentials löschen
- Credentials bearbeiten
- Credentials Passwörter verschlüsselt ans Backend schicken
s
### Todo:
- Credentials anzeigen
- Uuid check
- Passwörter in den Credentials entschlüsseln

## Weitere sicherheits Möglichkeiten
- Beim einloggen, anzahl versuche maximieren
- Backend gibt nur einen response, falls Frontend die gleiche Uuid hat (in arbeit)
