package com.example.BazaDanychTwoFactor.data;

import com.example.BazaDanychTwoFactor.repository.*;

/**
 * Klasa umożliwia wypełnienie bazy odpowiednimi danymi
 */
public class Seed {

    public Seed(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    /**
     * Wypełnia baze danymi o restauracjach, daniach oraz zniżkach
     */
    public void seedData() {

        }

}
