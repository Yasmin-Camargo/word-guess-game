import axios from 'axios';

const API_URL = "http://localhost:8080/words";

/**
 * Custom hook to manage the removal of words from the backend.
 * @returns {Object} Returns an object containing the removeWord function.
 */
export const useRemoveWord = () => {
  /**
   * Removes a word from the backend by making a DELETE request.
   * @param {number} id - The ID of the word to be removed.
   * @returns {Promise<Object>} Returns the response data from the server.
   * @throws {Error} Throws an error if the request fails.
   */
  const removeWord = async (id: number) => {
    try {
      const response = await axios.delete(`${API_URL}/${id}`);
      return response.data; 
    } catch (error) {
      console.error('Erro ao excluir palavra:', error);
      throw error; 
    }
  };

  return { removeWord }; 
};
