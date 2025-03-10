import axios from 'axios';
import { GameConfig } from '../interface/GameConfig';

const API_URL = "http://localhost:8080/game/config";

export const saveWord = async (newGameConfig: GameConfig) => {
  try {
    const response = await axios.post(API_URL, newGameConfig); 
    return response.data; 
  } catch (error) {
    console.error('Erro ao setar as confugrações', error);
    throw error; 
  }
};
