select
    c.nome,
    sum(m.soldi)
from Casse c
left outer join movimenti m on c.nome = m.tipo
group by
    c.nome
order by
    c.nome