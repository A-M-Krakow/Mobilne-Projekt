
# Rejestrator rezerwacji mieszkania na krótkoterminowe pobyty (na doby).

## Cel aplikacji:
Aplikacja powstała w celu umożliwienia  odczytu zapytań dotyczących wynajmu mieszkania z bazy danych, które zostały tam wysłane przez potencjalnych klientów za pomocą strony internetowej http://www.a-m.netstrefa.pl.
Umożliwia ona przeglądanie zapytań, usuwanie ich oraz przekształcenie każdego z nich w rezerwację.
Rezerwacje przechowywane są w wewnętrznej bazie danych sqlite. Za pomocą aplikacji można je modyfikować oraz usuwać. 
Aplikacja, w określonych odstępach czasu, sprawdza dostępność nowych zapytań w bazie danych i w przypadku ich istnienia generuje na urządzeniu powiadomienie.
Możliwe jest także ustawianie cen poszczególnych opcji pobytu, które są wysyłane do bazy danych (aby były widoczne dla klientów) oraz są przypisywane do każdej nowej rezerwacji.
W każdej z rezerwacji możliwe jest ustawienie cen indywidualnie.

## Opis aplikacji:
##### Główny ekran (MainActivity):
Pozwala na wybór jednej z trzech możliwości:
* Wyświetlenie listy zapytań.
* Wyświetlenie listy rezerwacji.
* Wyświetlenie ekranu ustawiania cen.

Gówna aktywność, w stałych odstępach czasu łączy się z plikiem php obsługującym zewnętrzną bazę danych i wysyła mu identyfikator ostatniego zapytania, które było pobrane przez aplikację. 
Jeśli otrzyma odpowiedź, że istnieją zapytania późniejsze, generuje powiadomienie na urządzeniu.

##### Pozostałe ekrany:
1) Ekran "pokaż zapytania" (QueriesActivity) wyświetla listę zapytań od potencjalnych klientów.
Aplikacja łączy się za pośrednictwem Internetu z plikiem PHP, który przekazuje jej pobrane z bazy danych msql informacje o zapytaniach w formacie XML.
Odpowiedź serwera zostaje następnie przekształcona w listę nodów ze szczegółami zapytań a opis  każdego zapytania ( daty pobytu oraz ilość dni) umieszczany jest na liście. Można przeglądać szczegóły elementów listy oraz usuwać wpisy. 

        a) Ekran "zapytanie" (QueryActivity) umożliwia przeglądanie szczegółów każdego z zapytań, kontakt z klientem i przekształcenie zapytania w rezerwację. 
        
2) Ekran "pokaż rezerwacje" (BookingsActivity) wyświetla listę zapisanych na urządzeniu rezerwacji od klientó. Rezerwacje są zapisywane w wewnętrznej bazie sqlite. Można przeglądać szczegóły rezerwacji, albo usunąć rezerwację z listy.
        
        a) Ekran "rezerwacja" (BookingActivity) umożliwia modyfikowanie rezerwacji oraz podliczanie cen a także kontakt z klientem.
        
3) Ekran aktywowany za pomocą przycisku "ustaw ceny" pozwala na zapisanie w pamięci urządzenia (sharedPreferences) cen, które są automatycznie przypisywane do 
nowych rezerwacji oraz są wysyłane na serwer (widoczne dla klientów na stronie). 
Nowa rezerwacja zawsze ma przypisane ceny, które są ustawione na tym ekranie, jednak istnieje możliwość modyfikowania cen indywidualnie dla każdej z rezerwacji (na ekranie "rezerwacja").


## Autor aplikacji:
Anna Madej 189490 KrZZIs3011Io  


# Instrukcja obsługi:
## POKAŻ ZAPYTANIA  (QueriesActivity).
Przycisk ten powoduje wyświetlenie listy zapytań klientów.   
Istnieje możliwość, że lista zapytań nie zostanie wygenerowana.  
Może się tak zdarzyć w dwóch przypadkach:  
1) Brak połączenia z internetem - należy zapewnić połączenie.  
2) Brak cen zapisanych w pamięci urządzenia - należy najpierw ustawić ceny.  

Kliknięcie ikony kosza obok zapytania spowoduje usunięcie go z serwera.
Kliknięcie dat zapytania spowoduje wyświetlenie jego szczegółów:
* "Zapytanie" (QueryActivity) 
Wyświetla szczegóły zapytania oraz umożliwia skontaktowanie się z osobą, która je wysłała (wysłanie wiadomości email lub wykonanie telefonu - odpowiednie przyciski).
Kliknięcie przycisku "rezerwuj" spowoduje przekształcenie zapytania w rezerwację oraz usunięcie go z bazy zapytań na serwerze.

    * "Rezerwacja:" (BookingActivity)
Ekran rezerwacji umożliwia edycję jej szczegółów z jednoczesnym obliczaniem całkowitej kwoty rezerwacji.  Jest on bliźniaczy do ekranu rezerwacji opisanego pod opcją "pokaż rezerwacje" (patrz niżej)  z tą różnicą, że w przypadku nowej rezerwacji zostanie ona dopisana do bazy sqlite, natomiast rezerwacja już istniejąca, po użyciu przycisku "zapisz" zostanie po prostu zaktualizowana.

## POKAŻ REZERWACJE (BookingsActivity)  
Przycisk ten powoduje wyświetlenie  listy rezerwacji, które są zapisane w bazie sqlite na urządzeniu. 
Na ekranie "Rezerwacje" można wybrać interesujący nas wpis i wyświetlić jego szczegóły.  
Kliknięcie ikony kosza obok rezerwacji spowoduje usunięcie jej z listy.
* Rezerwacja (BookingActivity)  
Ekran ten umożliwia edycję szczegółów rezerwacji z jednoczesnym obliczaniem całkowitej kwoty do zapłaty. 
Na tym ekranie można też wysłać wiadomość albo wykonać telefon do klienta (odpowiednie przyciski). 
Wybór dat pobytu realizowany jest poprzez graficzną reprezentację kalendarza - datepicker.
Każda zmiana parametrów rezerwacji, która mogłaby wpłynąć na jej końcową kwotę, jest automatycznie uwzględniana w znajdującym się na dole ekranu polu "cena całkowita".
Po wciśnięciu przycisku "zapisz" rezerwacja zostanie zapisana w wewnętrznej bazie danych sqlite. 

## USTAW CENY (PricesActivity)  
Ekran ten pozwala na zapisanie w pamięci urządzenia (w formie shared preferences) cen wynajmu, które są przypisywane do każdej nowej rezerwacji. 
Ceny te są również wysyłane za pośrednictwem Internetu do bazy danych mysql a następnie są wyświetlane klientom na stronie internetowej mieszkania.
Jeżeli połączenie internetowe jest niedostępne, ceny nie zostaną zaktualizowane.
