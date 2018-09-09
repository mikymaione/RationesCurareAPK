select
	sum(soldi)
from movimenti
where
    tipo = :tipo