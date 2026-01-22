import { Routes } from '@angular/router';
import {PersonsList} from './features/persons/persons-list/persons-list';
import {PersonDetail} from './features/persons/person-detail/person-detail';

export const routes: Routes = [
  { path: '', redirectTo: 'persons', pathMatch: 'full' },
  { path: 'persons', component: PersonsList },
  {
    path: 'persons/new',
    loadComponent: () =>
      import('./features/persons/person-create/person-create')
        .then(m => m.PersonCreate)
  },
  { path: 'persons/:id', component: PersonDetail }

];
