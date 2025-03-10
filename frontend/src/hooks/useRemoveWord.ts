import axios from 'axios';

const API_URL = "http://localhost:8080/words";

export const useRemoveWord = () => {

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
