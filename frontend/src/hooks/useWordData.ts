import axios, { AxiosPromise } from "axios";
import { WordData } from "../interface/WordData";
import { useQuery } from "@tanstack/react-query";

const API_URL = "http://localhost:8080";

const fetchData = async (): Promise<WordData[]> => {
  const response = await axios.get<WordData[]>(`${API_URL}/words`);
  return response.data;
};


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
