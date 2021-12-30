library ieee;
use ieee.std_logic_1164.all;
use IEEE.std_logic_signed.all;
use ieee.numeric_std.all; 

entity Escolha is
	port(vet_sel: in std_logic_vector(7 downto 0);
		S0, S3: out std_logic_vector(2 downto 0);
		S1, S2: out std_logic_vector(5 downto 0);
		sinalA0, sinalA1: out std_logic
		);
end Escolha;


architecture comportamento of Escolha is
	signal aux8: signed (1 downto 0);
	begin
	process (vet_sel)
	begin

		case vet_sel is


------------------------- Filtro 1------------------------------

			when "00000000" => --[0, 0, 0, 128, 0, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "001001";
				S4 <= "001001";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00000001" => --[0, 2, -4, 108, 16, 4, 2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "010";
				S3 <= "000111";
				S4 <= "000001";
				S5 <= "010";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00000010" => --[0, 4, -16, 108, 44, -16, 4, 0]
				S0 <= "000";
				S1 <= "010";
				S2 <= "100";
				S3 <= "000111";
				S4 <= "000100";
				S5 <= "100";
				S6 <= "010";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00000011" => --[0, 2, -16, 84, 64, -8, 2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "100";
				S3 <= "000110";
				S4 <= "000101";
				S5 <= "011";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00000100" => --[0, 4, -32, 84, 84, -16, 4, 0]
				S0 <= "000";
				S1 <= "010";
				S2 <= "101";
				S3 <= "000110";
				S4 <= "000110";
				S5 <= "100";
				S6 <= "010";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00000101" => --[0, 2, -8, 64, 84, -16, 2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "011";
				S3 <= "000101";
				S4 <= "000110";
				S5 <= "100";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00000110" => --[0, 4, -16, 44, 108, -16, 4, 0]
				S0 <= "000";
				S1 <= "010";
				S2 <= "100";
				S3 <= "000100";
				S4 <= "000111";
				S5 <= "100";
				S6 <= "010";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00000111" => --[0, 2, 4, 16, 108, -4, 2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "010";
				S3 <= "000001";
				S4 <= "000111";
				S5 <= "010";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';

------------------------- Filtro 2------------------------------

			when "00001000" => --[0, 0, 0, 128, 0, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "001001";
				S4 <= "001001";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00001001" => --[0, 2, 32, 64, 20, 8, 2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "101";
				S3 <= "000101";
				S4 <= "000010";
				S5 <= "011";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00001010" => --[0, -2, 32, 64, 32, 4, -2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "101";
				S3 <= "000101";
				S4 <= "000011";
				S5 <= "010";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '1';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '1';
				sinalA7 <= '0';
			when "00001011" => --[0, 2, 32, 44, 32, 16, 2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "101";
				S3 <= "000100";
				S4 <= "000011";
				S5 <= "100";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00001100" => --[0, 4, 16, 44, 44, 16, 4, 0]
				S0 <= "000";
				S1 <= "010";
				S2 <= "100";
				S3 <= "000100";
				S4 <= "000100";
				S5 <= "100";
				S6 <= "010";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00001101" => --[0, 2, 16, 32, 44, 32, 2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "100";
				S3 <= "000011";
				S4 <= "000100";
				S5 <= "101";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00001110" => --[0, -2, 4, 32, 64, 32, -2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "010";
				S3 <= "000011";
				S4 <= "000101";
				S5 <= "101";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '1';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '1';
				sinalA7 <= '0';
			when "00001111" => --[0, 2, 8, 20, 64, 32, 2, 0]
				S0 <= "000";
				S1 <= "001";
				S2 <= "011";
				S3 <= "000010";
				S4 <= "000101";
				S5 <= "101";
				S6 <= "001";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';

------------------------- Filtro 3------------------------------

			when "00010000" => --[0, 0, 0, 128, 0, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "001001";
				S4 <= "001001";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00010001" => --[-2, 4, -2, 108, 16, -2, 4, 2]
				S0 <= "001";
				S1 <= "010";
				S2 <= "001";
				S3 <= "000111";
				S4 <= "000001";
				S5 <= "001";
				S6 <= "010";
				S7 <= "001";
				sinalA0 <= '1';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00010010" => --[-2, 2, -16, 108, 44, -8, 2, -2]
				S0 <= "001";
				S1 <= "001";
				S2 <= "100";
				S3 <= "000111";
				S4 <= "000100";
				S5 <= "011";
				S6 <= "001";
				S7 <= "001";
				sinalA0 <= '1';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '1';
			when "00010011" => --[-2, 4, -32, 108, 64, -16, 4, -2]
				S0 <= "001";
				S1 <= "010";
				S2 <= "101";
				S3 <= "000111";
				S4 <= "000101";
				S5 <= "100";
				S6 <= "010";
				S7 <= "001";
				sinalA0 <= '1';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '1';
			when "00010100" => --[-2, 8, -32, 84, 84, -16, 4, -2]
				S0 <= "001";
				S1 <= "011";
				S2 <= "101";
				S3 <= "000110";
				S4 <= "000110";
				S5 <= "100";
				S6 <= "010";
				S7 <= "001";
				sinalA0 <= '1';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '1';
			when "00010101" => --[-2, 4, -16, 64, 108, -32, 4, -2]
				S0 <= "001";
				S1 <= "010";
				S2 <= "100";
				S3 <= "000101";
				S4 <= "000111";
				S5 <= "101";
				S6 <= "010";
				S7 <= "001";
				sinalA0 <= '1';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '1';
			when "00010110" => --[-2, 2, -8, 44, 108, -16, 2, -2]
				S0 <= "001";
				S1 <= "001";
				S2 <= "011";
				S3 <= "000100";
				S4 <= "000111";
				S5 <= "100";
				S6 <= "001";
				S7 <= "001";
				sinalA0 <= '1';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '1';
			when "00010111" => --[2, 4, -2, 16, 108, -2, 4, -2]
				S0 <= "001";
				S1 <= "010";
				S2 <= "001";
				S3 <= "000001";
				S4 <= "000111";
				S5 <= "001";
				S6 <= "010";
				S7 <= "001";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '1';

------------------------- Filtro 4------------------------------

			when "00011000" => --[0, 0, 0, 128, 0, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "001001";
				S4 <= "001001";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00011001" => --[0, 0, 0, 108, 20, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "000111";
				S4 <= "000010";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00011010" => --[0, 0, 0, 108, 20, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "000111";
				S4 <= "000010";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00011011" => --[0, 0, 0, 84, 44, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "000110";
				S4 <= "000100";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00011100" => --[0, 0, 0, 64, 64, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "000101";
				S4 <= "000101";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00011101" => --[0, 0, 0, 44, 84, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "000100";
				S4 <= "000110";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00011110" => --[0, 0, 0, 20, 108, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "000010";
				S4 <= "000111";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00011111" => --[0, 0, 0, 20, 108, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "000010";
				S4 <= "000111";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';

------------------------- Filtro 5------------------------------

			when "00100000" => --[0, 0, 0, 128, 0, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "001001";
				S4 <= "001001";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00100001" => --[0, 0, 2, 108, 16, 2, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "001";
				S3 <= "000111";
				S4 <= "000001";
				S5 <= "001";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00100010" => --[0, 0, -16, 108, 44, -8, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "100";
				S3 <= "000111";
				S4 <= "000100";
				S5 <= "011";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00100011" => --[0, 0, -16, 84, 64, -4, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "100";
				S3 <= "000110";
				S4 <= "000101";
				S5 <= "010";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00100100" => --[0, 0, -32, 84, 84, -8, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "101";
				S3 <= "000110";
				S4 <= "000110";
				S5 <= "011";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00100101" => --[0, 0, -4, 64, 84, -16, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "010";
				S3 <= "000101";
				S4 <= "000110";
				S5 <= "100";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00100110" => --[0, 0, -8, 44, 108, -16, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "011";
				S3 <= "000100";
				S4 <= "000111";
				S5 <= "100";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '1';
				sinalA5 <= '1';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00100111" => --[0, 0, 2, 16, 108, 2, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "001";
				S3 <= "000001";
				S4 <= "000111";
				S5 <= "001";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';

------------------------- Filtro 6------------------------------

			when "00101000" => --[0, 0, 0, 128, 0, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "001001";
				S4 <= "001001";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00101001" => --[0, 0, 32, 64, 32, 0, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "101";
				S3 <= "000101";
				S4 <= "000011";
				S5 <= "000";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00101010" => --[0, 0, 16, 64, 44, 4, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "100";
				S3 <= "000101";
				S4 <= "000100";
				S5 <= "010";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00101011" => --[0, 0, 16, 64, 44, 4, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "100";
				S3 <= "000101";
				S4 <= "000100";
				S5 <= "010";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00101100" => --[0, 0, 32, 44, 44, 8, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "101";
				S3 <= "000100";
				S4 <= "000100";
				S5 <= "011";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00101101" => --[0, 0, 4, 44, 64, 16, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "010";
				S3 <= "000100";
				S4 <= "000101";
				S5 <= "100";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00101110" => --[0, 0, 4, 44, 64, 16, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "010";
				S3 <= "000100";
				S4 <= "000101";
				S5 <= "100";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when "00101111" => --[0, 0, 0, 32, 64, 32, 0, 0]
				S0 <= "000";
				S1 <= "000";
				S2 <= "000";
				S3 <= "000011";
				S4 <= "000101";
				S5 <= "101";
				S6 <= "000";
				S7 <= "000";
				sinalA0 <= '0';
				sinalA1 <= '0';
				sinalA2 <= '0';
				sinalA5 <= '0';
				sinalA6 <= '0';
				sinalA7 <= '0';
			when others =>
				aux8 <= "00";
		
		end case;

	end process;

end comportamento;