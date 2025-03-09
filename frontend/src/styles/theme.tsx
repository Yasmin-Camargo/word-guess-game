import { extendTheme } from '@chakra-ui/react';

const theme = extendTheme({
  styles: {
    global: {
      body: {
        bgGradient: 'linear(to-r, #8d2c91, #1815b6)',
      },
    },
  },
});

export default theme;
