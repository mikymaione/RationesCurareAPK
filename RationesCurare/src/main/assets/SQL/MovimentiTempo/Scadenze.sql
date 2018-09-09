SELECT
	*
FROM MovimentiTempo
where
	(GiornoDelMese >= :GiornoDa and GiornoDelMese <= :GiornoA) or
	(PartendoDalGiorno >= :GiornoDa and PartendoDalGiorno <= :GiornoA)