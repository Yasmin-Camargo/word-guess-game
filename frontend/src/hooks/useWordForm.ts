import axios from 'axios';
import { WordData } from '../interface/WordData';

const API_URL = "http://localhost:8080/words";

/**
 * Saves a new word to the backend.
 * @param {WordData} newWordData - The data of the new word to be saved.
 * @returns {Promise<Object>} Returns a promise that resolves with the response data from the server.
 * @throws {Error} Throws an error if the request fails.
 */
export const saveWord = async (newWordData: WordData) => {
  try {
    const response = await axios.post(API_URL, newWordData); 
    return response.data; 
  } catch (error) {
    console.error('Erro ao adicionar palavra:', error);
    throw error; 
  }
};
