-- Taulukko ravintoiloille
CREATE TABLE restaurant (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    url TEXT NOT NULL,
    campus VARCHAR(50)
);