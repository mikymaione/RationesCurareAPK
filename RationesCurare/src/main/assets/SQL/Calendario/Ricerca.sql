select
	*
from Calendario
where
    ((0 = :bData) or (Giorno >= :DA and Giorno <= :A))
order by
    Giorno,
    Descrizione