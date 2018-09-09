select
	sum(soldi)
from movimenti
where
    ('' = :tipo or tipo = :tipo) and
    ((0 = :bData) or (data >= :dataDA and data <= :dataA)) and
    ((0 = :bSoldi) or (soldi >= :soldiDA and soldi <= :soldiA)) and
    (descrizione like :descrizione) and
    (MacroArea like :MacroArea)