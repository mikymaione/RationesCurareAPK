select
    MacroArea
from movimenti
where
    descrizione like :descrizione
order by
    MacroArea