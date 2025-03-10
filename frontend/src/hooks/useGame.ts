import { useState } from 'react';
import axios from 'axios';
import { GameData } from "../interface/GameData";

const API_URL = "http://localhost:8080/game";


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

export const useCheckWord = () => {
  const [message, setMessage] = useState('');
  const [finish, setFinish] = useState(false);


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
