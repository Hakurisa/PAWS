DELIMITER //

CREATE PROCEDURE GetSongsInPlaylist(IN playlist_id INT)
BEGIN
    SELECT s.Jmeno AS SongName, s.Audioslozka AS AudioFile, s.Coverimage AS CoverImage
    FROM Skladba s
    INNER JOIN PlaylistSkladba ps ON s.SkladbaID = ps.SkladbaID
    WHERE ps.PlaylistID = playlist_id;GetSongsInPlaylist
END//

DELIMITER ;