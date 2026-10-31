-- Taulukko ravintoiloille
CREATE TABLE restaurant (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    url TEXT NOT NULL,
    campus VARCHAR(50)
--     weekMenu tänne
);