export interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  roles: string[]; // Set<String> u Javi odgovara nizu stringova u TypeScriptu
}
