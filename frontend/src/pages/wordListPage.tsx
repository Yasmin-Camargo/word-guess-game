import { Box, Button } from '@chakra-ui/react';
import { useWordData } from '../hooks/useWordData';
import { Card } from '../components/wordcard';

export const WordList = () => {
  const { data, refetch } = useWordData();

  return (
    <Box maxW="1280px" mx="auto" p={8} textAlign="center">
      <Box display="flex" justifyContent="flex-end">
      <Button colorScheme="white" size="md" onClick={() => refetch()} leftIcon={<span style={{ fontSize: '1.5rem' }}>🔄</span>}>
        Atualizar
      </Button>
          
      </Box>
      <Box
        display="grid"
        gridTemplateColumns="repeat(auto-fill, minmax(250px, 1fr))"
        gap={4}
        mt={8}
      >
        {data?.map((wordData) => (
          <Card key={wordData.idWord} wordData={wordData} refetch={refetch} />  
        ))}
      </Box>
    </Box>
  );
};
