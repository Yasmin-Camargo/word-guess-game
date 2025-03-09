import axios, { AxiosPromise } from "axios";
import { WordData } from "../interface/WordData";
import { useQuery } from "@tanstack/react-query";

const API_URL = "http://localhost:8080";

/**
 * Fetches the list of words from the backend.
 * @returns {Promise<WordData[]>} Returns a promise that resolves with an array of WordData.
 * @throws {Error} Throws an error if the request fails.
 */
const fetchData = async (): Promise<WordData[]> => {
  const response = await axios.get<WordData[]>(`${API_URL}/words`);
  return response.data;
};

/**
 * Custom hook to manage the fetching of word data using React Query.
 * @returns {Object} Returns an object containing the word data, refetch function, loading status, and error status.
 */
export function useWordData() {
  const query = useQuery({
    queryFn: fetchData, 
    queryKey: ['word-data'], 
    retry: 2 
  });

  return { 
    data: query.data,       
    refetch: query.refetch, 
    isLoading: query.isLoading, 
    isError: query.isError   
  };
}
