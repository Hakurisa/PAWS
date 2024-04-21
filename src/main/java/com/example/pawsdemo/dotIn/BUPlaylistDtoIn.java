package com.example.pawsdemo.dotIn;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

public class BUPlaylistDtoIn {

    @Id
    @NotNull
    private int id;

    @NotNull
    private int playlistId;

    @NotNull
    private int beznyUzivatelId;
}
