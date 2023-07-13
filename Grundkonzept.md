# Grundkonzept für die sichere Speicherung von Passwörtern

## Beschreibung:
Das folgende Grundkonzept beschreibt einen Ansatz zur sicheren Speicherung von Passwörtern in einer Datenbank. Es verwendet symmetrische Verschlüsselungstechniken, um die Passwörter zu schützen und ermöglicht den Nutzern, ihre Passwörter bei Bedarf wiederherzustellen.

## Schritte:

1. Registrierung:

- Der Nutzer gibt sein Masterpasswort ein.
- Ein symmetrischer MasterKey wird aus dem eingegebenen Masterpasswort generiert.
- Die anderen Passwörter werden mit dem MasterKey verschlüsselt, bevor sie in der Datenbank gespeichert werden.
- Der verschlüsselte MasterKey wird sicher in der Datenbank zusammen mit anderen relevanten Informationen gespeichert, wie Benutzernamen und IDs.

2. Zukünftige Anmeldungen:

- Sobald ein Nutzer versucht sich einzuloggen, wird ein request ans Backend geschickt, als Antwort kommt eine verschlüsselte UUID zurück und speichert diese im User auf der Datenbank.
- Die UUID wird entschlüsselt.
- Die UUID wird mit einem neuen Passwort verschlüsselt.
- Bei jedem Request wird die UUID erneut verschlüsselt ans Backend geschickt.
- Jetzt wird der selbe Request erneut aber diesmal mit der verschlüsselten UUID geschickt
- Im Backend wird die UUID entschlüsselt und mit der UUID, welche sich in der Datenbank befindet abgeglichen.
- Der verschlüsselte MasterKey wird aus der Datenbank abgerufen und ans Frontend gesendet.
- Der Nutzer gibt sein Masterpasswort erneut ein.
- Das erneut eingegebene Masterpasswort wird verwendet, um den MasterKey wiederherzustellen.

- Passwortentschlüsselung:
- Der wiederhergestellte MasterKey wird verwendet, um die anderen Passwörter aus der Datenbank zu entschlüsseln.
- Die entschlüsselten Passwörter werden dem Nutzer angezeigt.

## Sicherheitsaspekte:

1. Verschlüsselung:
- Die Passwörter werden mit einem starken symmetrischen MasterKey verschlüsselt, der aus dem Masterpasswort generiert wird.
- Eine bewährte Verschlüsselungstechnik sollte verwendet werden, um die Sicherheit des MasterKeys und der verschlüsselten Passwörter zu gewährleisten.

2. Sicherer Speicher:
- Der verschlüsselte MasterKey und andere Informationen werden sicher in der Datenbank gespeichert, um unbefugten Zugriff zu verhindern.
- Geeignete Sicherheitsmaßnahmen wie Zugriffskontrollen und Verschlüsselung können angewendet werden.

3. Schutz des Masterpassworts:
- Der Nutzer sollte angeleitet werden, ein starkes und einzigartiges Masterpasswort zu wählen, um die Sicherheit zu gewährleisten.
- Sicherheitsvorkehrungen wie Passwortrichtlinien und Zwei-Faktor-Authentifizierung können implementiert werden, um das Masterpasswort zu schützen.

4. Sessions:
- Durch die verschlüsselte Session UUID, kann kein Nutzer die Daten eines anderen Nutzers einsehen, extrene Programme oder abfragen der Entpoints werden als nicht autorisiert erkannt und erhalten dementsprechend keine Daten
