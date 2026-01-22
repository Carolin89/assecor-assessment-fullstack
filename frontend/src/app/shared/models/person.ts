export interface Person {
  id: number;
  firstName: string;
  lastName: string;
  zipCode?: string | null;
  city?: string | null;
  favoriteColor?: string | null;
}
