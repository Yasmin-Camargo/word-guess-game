import { useState } from 'react';
import axios from 'axios';
import { GameData } from "../interface/GameData";

const API_URL = "http://localhost:8080/game";

/**
 * Starts the game by making a request to the backend.
 * @returns {Promise<GameData>} Returns a promise that resolves with the game data.
 * @throws {Error} Throws an error if the request fails.
 */
export const startGame = (): Promise<GameData> => {
  return axios.get<GameData>(`${API_URL}/start`)
    .then(response => {
      return response.data; 
    })
    .catch(error => {
      console.error('Erro ao iniciar jogo:', error);
      throw error; 
    });
};

/**
 * Custom hook to check a word with the backend and update the application state.
 * @returns {Object} Returns an object containing the message, game finish status, and the function to check the word.
 */
export const useCheckWord = () => {
  const [message, setMessage] = useState('');
  const [finish, setFinish] = useState(false);

  /**
   * Verifies if the entered word is correct by making a request to the backend.
   * @param {string} word - The word to be checked.
   * @returns {Promise<void>} Updates the game state and message based on the backend response.
   * @throws {Error} Throws an error if the request fails.
   */
  const checkWord = async (word: string) => {
    try {
      const response = await axios.get(`${API_URL}/check/${word}`);
      setMessage(response.data);

      if (response.data.includes('acertou') || response.data.includes('acabaram')) {
        setFinish(true);
      }
    } catch (error) {
      setFinish(false);
      console.error('Erro ao verificar a palavra:', error);
      setMessage('Erro ao verificar a palavra.');
    }
  };

  return {
    message,
    setMessage,
    finish,
    setFinish,
    checkWord,
  };
};
