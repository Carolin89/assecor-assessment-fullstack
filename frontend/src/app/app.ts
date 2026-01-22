import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {PersonsList} from './features/persons/persons-list/persons-list';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PersonsList],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
