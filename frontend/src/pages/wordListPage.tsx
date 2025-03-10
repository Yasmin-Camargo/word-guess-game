import { Box, Button, useDisclosure } from '@chakra-ui/react';
import { useWordData } from '../hooks/useWordData';
import { Card } from '../components/wordcard';

export const WordList = () => {
  const { data, refetch } = useWordData();

  return (
    <Box maxW="1280px" mx="auto" p={8} textAlign="center">
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
