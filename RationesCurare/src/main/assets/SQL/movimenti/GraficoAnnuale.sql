select
    strftime('%Y', data) as anno,
    sum(soldi) as importo
from movimenti
group by
    strftime('%Y', data)