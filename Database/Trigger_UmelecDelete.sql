-- Scary!
DELIMITER //
CREATE TRIGGER delete_artist_trigger
AFTER DELETE ON Umelec
FOR EACH ROW
BEGIN
    -- Smazání záznamů uživatelů, kteří jsou spojeni s mazaným umělcem
    DELETE FROM Uzivatel WHERE UmelecID = OLD.UmelecID;
    
    -- Smazání záznamů v tabulce Au_a asociovaných s mazaným umělcem
    DELETE FROM Au_a WHERE UmelecID = OLD.UmelecID;
    
    -- Smazání alb asociovaných s mazanými záznamy v tabulce Au_a
    DELETE FROM Album WHERE AlbumID IN (SELECT AlbumID FROM Au_a WHERE UmelecID = OLD.UmelecID);
    
    -- Smazání skladeb asociovaných s mazanými alby
    DELETE FROM Skladba WHERE AlbumID IN (SELECT AlbumID FROM Au_a WHERE UmelecID = OLD.UmelecID);
    
    -- Smazání recenzí asociovaných s mazanými umělci
    DELETE FROM Recenze WHERE UmelecID = OLD.UmelecID OR AlbumID IN (SELECT AlbumID FROM Au_a WHERE UmelecID = OLD.UmelecID);
    
    -- Smazání záznamů z PlaylistSkladba, kde jsou mazané SkladbaID
    DELETE FROM PlaylistSkladba WHERE SkladbaID IN (SELECT SkladbaID FROM Skladba WHERE AlbumID IN (SELECT AlbumID FROM Au_a WHERE UmelecID = OLD.UmelecID));
END//
DELIMITER ;
