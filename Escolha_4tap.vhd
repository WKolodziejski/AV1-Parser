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

			when "00000000" => --[0, 128, 0, 0]
				S0 <= "000";
				S1 <= "001001";
				S2 <= "001001";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00000001" => --[-4, 108, 16, 8]
				S0 <= "010";
				S1 <= "000111";
				S2 <= "000001";
				S3 <= "011";
				sinalA0 <= '1';
				sinalA3 <= '0';
			when "00000010" => --[-8, 108, 44, -16]
				S0 <= "011";
				S1 <= "000111";
				S2 <= "000100";
				S3 <= "100";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00000011" => --[-16, 84, 64, -4]
				S0 <= "100";
				S1 <= "000110";
				S2 <= "000101";
				S3 <= "010";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00000100" => --[-32, 84, 84, -8]
				S0 <= "101";
				S1 <= "000110";
				S2 <= "000110";
				S3 <= "011";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00000101" => --[-4, 64, 84, -16]
				S0 <= "010";
				S1 <= "000101";
				S2 <= "000110";
				S3 <= "100";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00000110" => --[-16, 44, 108, -8]
				S0 <= "100";
				S1 <= "000100";
				S2 <= "000111";
				S3 <= "011";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00000111" => --[8, 16, 108, -4]
				S0 <= "011";
				S1 <= "000001";
				S2 <= "000111";
				S3 <= "010";
				sinalA0 <= '0';
				sinalA3 <= '1';
------------------------- Filtro 2------------------------------

			when "00001000" => --[0, 128, 0, 0]
				S0 <= "000";
				S1 <= "001001";
				S2 <= "001001";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00001001" => --[32, 64, 32, 0]
				S0 <= "101";
				S1 <= "000101";
				S2 <= "000011";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00001010" => --[32, 64, 32, 0]
				S0 <= "101";
				S1 <= "000101";
				S2 <= "000011";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00001011" => --[16, 64, 32, 16]
				S0 <= "100";
				S1 <= "000101";
				S2 <= "000011";
				S3 <= "100";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00001100" => --[32, 44, 44, 8]
				S0 <= "101";
				S1 <= "000100";
				S2 <= "000100";
				S3 <= "011";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00001101" => --[16, 32, 64, 16]
				S0 <= "100";
				S1 <= "000011";
				S2 <= "000101";
				S3 <= "100";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00001110" => --[0, 32, 64, 32]
				S0 <= "000";
				S1 <= "000011";
				S2 <= "000101";
				S3 <= "101";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00001111" => --[0, 32, 64, 32]
				S0 <= "000";
				S1 <= "000011";
				S2 <= "000101";
				S3 <= "101";
				sinalA0 <= '0';
				sinalA3 <= '0';
------------------------- Filtro 3------------------------------

			when "00010000" => --[0, 128, 0, 0]
				S0 <= "000";
				S1 <= "001001";
				S2 <= "001001";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00010001" => --[-8, 108, 32, -4]
				S0 <= "011";
				S1 <= "000111";
				S2 <= "000011";
				S3 <= "010";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00010010" => --[-16, 108, 44, -8]
				S0 <= "100";
				S1 <= "000111";
				S2 <= "000100";
				S3 <= "011";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00010011" => --[-32, 108, 64, -16]
				S0 <= "101";
				S1 <= "000111";
				S2 <= "000101";
				S3 <= "100";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00010100" => --[-32, 84, 84, -8]
				S0 <= "101";
				S1 <= "000110";
				S2 <= "000110";
				S3 <= "011";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00010101" => --[-16, 64, 108, -32]
				S0 <= "100";
				S1 <= "000101";
				S2 <= "000111";
				S3 <= "101";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00010110" => --[-8, 44, 108, -16]
				S0 <= "011";
				S1 <= "000100";
				S2 <= "000111";
				S3 <= "100";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00010111" => --[-4, 32, 108, -8]
				S0 <= "010";
				S1 <= "000011";
				S2 <= "000111";
				S3 <= "011";
				sinalA0 <= '1';
				sinalA3 <= '1';
------------------------- Filtro 4------------------------------

			when "00011000" => --[0, 128, 0, 0]
				S0 <= "000";
				S1 <= "001001";
				S2 <= "001001";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00011001" => --[0, 108, 20, 0]
				S0 <= "000";
				S1 <= "000111";
				S2 <= "000010";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00011010" => --[0, 108, 20, 0]
				S0 <= "000";
				S1 <= "000111";
				S2 <= "000010";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00011011" => --[0, 84, 44, 0]
				S0 <= "000";
				S1 <= "000110";
				S2 <= "000100";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00011100" => --[0, 64, 64, 0]
				S0 <= "000";
				S1 <= "000101";
				S2 <= "000101";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00011101" => --[0, 44, 84, 0]
				S0 <= "000";
				S1 <= "000100";
				S2 <= "000110";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00011110" => --[0, 20, 108, 0]
				S0 <= "000";
				S1 <= "000010";
				S2 <= "000111";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00011111" => --[0, 20, 108, 0]
				S0 <= "000";
				S1 <= "000010";
				S2 <= "000111";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
------------------------- Filtro 5------------------------------

			when "00100000" => --[0, 128, 0, 0]
				S0 <= "000";
				S1 <= "001001";
				S2 <= "001001";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00100001" => --[2, 108, 16, 2]
				S0 <= "001";
				S1 <= "000111";
				S2 <= "000001";
				S3 <= "001";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00100010" => --[-16, 108, 44, -8]
				S0 <= "100";
				S1 <= "000111";
				S2 <= "000100";
				S3 <= "011";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00100011" => --[-16, 84, 64, -4]
				S0 <= "100";
				S1 <= "000110";
				S2 <= "000101";
				S3 <= "010";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00100100" => --[-32, 84, 84, -8]
				S0 <= "101";
				S1 <= "000110";
				S2 <= "000110";
				S3 <= "011";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00100101" => --[-4, 64, 84, -16]
				S0 <= "010";
				S1 <= "000101";
				S2 <= "000110";
				S3 <= "100";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00100110" => --[-8, 44, 108, -16]
				S0 <= "011";
				S1 <= "000100";
				S2 <= "000111";
				S3 <= "100";
				sinalA0 <= '1';
				sinalA3 <= '1';
			when "00100111" => --[2, 16, 108, 2]
				S0 <= "001";
				S1 <= "000001";
				S2 <= "000111";
				S3 <= "001";
				sinalA0 <= '0';
				sinalA3 <= '0';
------------------------- Filtro 6------------------------------

			when "00101000" => --[0, 128, 0, 0]
				S0 <= "000";
				S1 <= "001001";
				S2 <= "001001";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00101001" => --[32, 64, 32, 0]
				S0 <= "101";
				S1 <= "000101";
				S2 <= "000011";
				S3 <= "000";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00101010" => --[16, 64, 44, 4]
				S0 <= "100";
				S1 <= "000101";
				S2 <= "000100";
				S3 <= "010";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00101011" => --[16, 64, 44, 4]
				S0 <= "100";
				S1 <= "000101";
				S2 <= "000100";
				S3 <= "010";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00101100" => --[32, 44, 44, 8]
				S0 <= "101";
				S1 <= "000100";
				S2 <= "000100";
				S3 <= "011";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00101101" => --[4, 44, 64, 16]
				S0 <= "010";
				S1 <= "000100";
				S2 <= "000101";
				S3 <= "100";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00101110" => --[4, 44, 64, 16]
				S0 <= "010";
				S1 <= "000100";
				S2 <= "000101";
				S3 <= "100";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when "00101111" => --[0, 32, 64, 32]
				S0 <= "000";
				S1 <= "000011";
				S2 <= "000101";
				S3 <= "101";
				sinalA0 <= '0';
				sinalA3 <= '0';
			when others =>
				aux8 <= "00";
		
		end case;

	end process;

end comportamento;