CREATE VIEW SkladbyZanry AS
SELECT 
    s.Jmeno AS Skladba,
    z.Nazev AS Zanr
FROM 
    Skladba s
JOIN 
    SkladbaZanr sz ON s.SkladbaID = sz.SkladbaID
JOIN 
    Zanr z ON sz.ZanrID = z.ZanrID;