
// Have fun troubleshooting this! xoxo

function playAlbum(albumId) {
    fetch("/api/album/" + albumId)
        .then(response => response.json())
        .then(data => {
            displaySongs(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function playPlaylist(playlistId)  {
    fetch("/api/playlist/" + playlistId)
    .then(response => response.json())
    .then(data => {
        displaySongsPlaylist(data);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function displaySongs(album) {
    const songArea = document.querySelector(".song-area");

    songArea.innerHTML = "";

    album.skladby.forEach(function(song) {
        const container = document.createElement("div");
        container.classList.add("col-sm-2");
        const card = document.createElement("div");
        card.classList.add("card", "sm-2", "shadow-sm");

        const image = document.createElement("img");
        image.src = song.coverimage;
        image.classList.add("card-img-top");
        image.alt = "Song Image";
        card.appendChild(image);

        const cardBody = document.createElement("div");
        cardBody.classList.add("card-body");

        const title = document.createElement("h5");
        title.classList.add("card-title");
        title.textContent = song.jmeno;
        cardBody.appendChild(title);

        const duration = document.createElement("p");
        duration.classList.add("card-text");
        duration.textContent = "Délka: " + song.delka;
        cardBody.appendChild(duration);

        const artist = document.createElement("p");
        artist.classList.add("card-text");
        artist.textContent = "Autor: " + album.umelec;
        cardBody.appendChild(artist);

        const playButton = document.createElement("button");
        playButton.classList.add("btn", "btn-success", "play-authority");
        playButton.textContent = "Play";
        playButton.onclick = populateAudioPlayer.bind(null, song.audioslozka, song.jmeno, album.umelec, song.skladbaId);
        cardBody.appendChild(playButton);
        card.appendChild(cardBody);
        container.appendChild(card);
        songArea.appendChild(container);
    });
}
function displaySongsPlaylist(album) {
    const songArea = document.querySelector(".song-area");

    songArea.innerHTML = "";

    album.skladby.forEach(function(song, index) {
        const container = document.createElement("div");
        container.classList.add("col-sm-2");
        const card = document.createElement("div");
        card.classList.add("card", "sm-2", "shadow-sm");

        const image = document.createElement("img");
        image.src = song.coverimage;
        image.classList.add("card-img-top");
        image.alt = "Song Image";
        card.appendChild(image);

        const cardBody = document.createElement("div");
        cardBody.classList.add("card-body");

        const title = document.createElement("h5");
        title.classList.add("card-title");
        title.textContent = song.jmeno;
        cardBody.appendChild(title);

        const duration = document.createElement("p");
        duration.classList.add("card-text");
        duration.textContent = "Délka: " + song.delka;
        cardBody.appendChild(duration);

        const artist = document.createElement("p");
        artist.classList.add("card-text");
        artist.textContent = "Autor: " + album.umelci[index];
        cardBody.appendChild(artist);

        const playButton = document.createElement("button");
        playButton.classList.add("btn", "btn-success", "play-authority");
        playButton.textContent = "Play";
        playButton.onclick = populateAudioPlayer.bind(null, song.audioslozka, song.jmeno, album.umelci[index], song.skladbaId);
        cardBody.appendChild(playButton);
        card.appendChild(cardBody);
        container.appendChild(card);
        songArea.appendChild(container);
    });
}
function populateAudioPlayer(audioslozka, jmeno, umelec, id) {
    const player = document.querySelector(".audio-zone");
    player.innerHTML = "";

    const infoZone = document.createElement("div");
    infoZone.classList.add("col-md-3");

    const songInfo = document.createElement("div");
    songInfo.classList.add("song-info");

    const songTitle = document.createElement("h6");
    const artistName = document.createElement("p");

    songTitle.textContent = jmeno;
    artistName.textContent = umelec;

    songInfo.appendChild(songTitle);
    songInfo.appendChild(artistName);
    infoZone.appendChild(songInfo);


    const audioZone = document.createElement("div");
    audioZone.classList.add("col-md-9");

    const audioPlayer = document.createElement("audio");
    audioPlayer.classList.add("col-md-9", "audio-player")
    audioPlayer.controls = true;
    audioPlayer.setAttribute('controlsList', 'nodownload');
    audioPlayer.autoplay = true;
    audioPlayer.id = "music-player";

    const audioSource = document.createElement("source");
    audioSource.src = audioslozka;
    audioSource.type = "audio/mp3";
    audioPlayer.appendChild(audioSource);
    audioZone.appendChild(audioPlayer);
    player.appendChild(infoZone);
    player.appendChild(audioZone);

    // Perform POST request
    fetch(`/skladba/${id}/increment`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({}) // No data to send
    })
}