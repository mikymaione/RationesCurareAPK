select
    strftime('%Y', data) as anno,
    strftime('%m', data) as mese,
    sum(soldi) as importo
from movimenti
group by
    strftime('%Y', data),
    strftime('%m', data)