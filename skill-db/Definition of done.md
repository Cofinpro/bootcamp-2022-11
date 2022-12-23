Definition of done

UnitTests für so viel wie möglich (TextCoverage >50%)
  wenn möglich IntegrationsTests
Persistenz (nicht nur Mockdaten)
Datenvalidierung + Error-Messages (Exception-Handling)
Entspricht Wireframes oder ist leicht umwandelbar
Responsive


Detailansicht: Ein neues Profil anlegen
  Backend
    - Persistenz (nicht nur Mockdaten)
    - Verknüpfung mit Frontend
    - Datenvalidierung
  Frontend
    - Datenvalidierung (mit roter Umrandung)
    - Entspricht Wireframes oder ist leicht umwandelbar
    - Store nutzen
    - Models nutzen (entsprechen denen im Backend)
    - responsive
    
Detailansicht: Ein Profil anzeigen
  Backend
    - Verknüpfung mit Frontend
  Frontend
    - funktionierender Router
    - Store nutzen
    - Models nutzen (entsprechen denen im Backend)
    - responsive
    
Detailansicht: bearbeiten
  Backend
    - Verknüpfung mit Frontend
    - Datenvalidierung
  Frontend
    - direkte Verarbeitung der Änderungen, Ansichtsmodus updated
    - Datenvalidierung (mit roter Umrandung)
    - Store nutzen
    - Models nutzen (entsprechen denen im Backend)
    - responsive
    
Übersicht mit allen Einträgen
  Backend
    - DTOs
    - Sorting (Alphabetisch nach Namen)
    - Verknüpfung mit Frontend
  Frontend
    - Entspricht Wireframes (ohne Fotos) oder ist leicht umwandelbar
    - Store nutzen
    - Models nutzen (entsprechen denen im Backend)
    - responsive
    
Detailansicht: löschen
  Backend
    - Verknüpfung mit Frontend
    - Response aus Backend nach Löschung
  Frontend
    - visuelle Rückmeldung nach Löschung
    - Store nutzen
    - responsive
    
Login Seite
  Backend
    - Verknüpfung mit Frontend
    - json Web-Token (realitätsnahe Security)
    - In-memory-User
  Frontend
    - Entspricht Wireframes oder ist leicht umwandelbar
    - Warnung bei falscher Eingabe
    - responsive
    
Administratoransicht: Übersicht der Nutzer
  Backend
    - User-Datentyp angelegt
    - Verknüpfung mit Frontend
  Frontend
    - User in Tabelle muss nicht für Login geeignet sein
    - responsive
