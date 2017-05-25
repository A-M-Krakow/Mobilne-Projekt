#### Programowanie Systemów Mobilnych -projekt  
# Rejestrator rezerwacji - opis i instrukcja obsługi aplikacji  

# Opis  
## Cel aplikacji  
Aplikacja została stworzona w celu obsługi rezerwacji krótkoterminowego pobytu w oferowanym do wynajmu mieszkaniu oraz aby umożliwić właścicielowi przegląd zapytań przesyłanych przez
potencjalnych klientów oraz zapisywanie tych zapytań w formie rezerwacji z możliwością edycji i kalkulacji cen.   
## Opis aplikacji  
Aplikacja składa się z czterech głównych ekranów - aktywności:  
* Główny ekran (MainActivity) pozwala na wybór jednej z trzech możliwości oferowanych przez opisane niżej aktywności.  
* Ekran aktywowany po naciśnięciu przycisku "pokaż zapytania" wyświetla pobraną z serwera php listę zapytań o rezerwacje, które były przesłane przez klientów
za pośrednictwem strony www.   
* Ekran aktywowany po naciśnięciu przycisku "pokaż rezerwacje" pozwala na przeglądanie i edycję tych zapytań, które zostały pobrane z serwera i przekształcone
w rezerwacje oraz zapisane w wewnętrznej bazie danych sqlite.    
* Ekran aktywowany za pomocą przycisku "ustaw ceny" pozwala na zapisanie w pamięci urządzenia (sharedPreferences) cen, któe będą automatycznie przypisywane do 
nowych rezerwacji.  

## Autor aplikacji  
1. Anna Madej 189490 KrZZIs3011Io  


# Instrukcja obsługi  
## POKAŻ ZAPYTANIA  (QueriesActivity)  
Przycisk ten powoduje wyświetlenie się listy zapytań klientów.   
Są one pobierane w formie pliku XML za pośrednictwem umieszczonego na zdalnym serwerze pliku PHP, pobierającego dane z bazy mysql, w której są zapisane zapytania.  
Dane te są następnie przekształcane w listę nodów, aby można było uzyskać dostęp do szczegółów każdego z zapytań.  
Istnieje możliwość, że lista zapytań nie zostanie wygenerowana.  
Może się tak zdarzyć w dwóch sytuacjach:  
* Brak połączenia z internetem - należy zapewnić połączenie.  
* Brak cen zapisanych w pamięci urządzenia - należy najpierw ustawić ceny.  
W obu przypadkach, na ekranie telefonu zostanie wyświetlona odpowiednia informacja (toast).  
Po wybraniu interesującego nas zapytania, zostają wyświetlone jego szczegóły.  

*Szczegóły zapytania: *  (QueryActivity)  
Na ekranie, który się pojawił można wykonać telefon do klienta albo wysłać do niego wiadomość e-mail (odpowiednie przyciski).  
Istnieje również przycisk "rezerwuj" przekształcający zapytanie w rezerwację, która będzie mogła być zapisana w pamięci urządzenia (baza sqlite).

*Rezerwacja: * (BookingActivity)
Ekran rezerwacji umożliwia edycję jej szczegółów z jednoczesnym obliczaniem całkowitej kwoty rezerwacji. 
Jest on bliźniaczy to ekranu rezerwacji opisanego pod opcją "POKAŻ REZERWACJE" z tą różnicą, że w przypadku nowej rezerwacji
zostanie ona dopisana do bazy sqlite, natomiast rezerwacja już istniejąca, po użyciu przycisku "zapisz" zostanie po prostu zaktualizowana.

## POKAŻ REZERWACJE (BookingsActivity)  
Przycisk ten powoduje wyświetlenie  listy zapisanych w bazie sqlite rezerwacji. 
Na ekranie "Rezerwacje" można wybrać interesujący nas wpis i wyświetlić jego szczegóły. 
*Szczegóły rezerwacji: * (BookingActivity)  
Ekran ten umożliwia edycję jej szczegółów z jednoczesnym obliczaniem całkowitej kwoty rezerwacji.
Na tym ekranie można też wysłać wiadomość albo wykonać telefon do klienta.
Wybór dat pobytu realizowany jest poprzez graficzną reprezentacjękalendarza - datepicker.
Każda zmiana parametrów rezerwacji, która mogłaby wpłynąć na jej końcową kwotę, jest automatycznie uwzględniana w znajdującym się na dole ekranu polu "cena całkowita".
Po wciśnięciu przycisku "zapisz" rezerwacja zostanie zapisana w wewnętrznej bazie danych sqlite.

## USTAW CENY (PricesActivity)  
Ekran ten pozwala na zapisanie w pamięci urządzenia (w formie shared preferences) cen wynajmu, do których dostęp mają inne aktywności.
