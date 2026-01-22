import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Person} from '../../shared/models/person';

@Injectable({
  providedIn: 'root',
})
export class PersonApi {
  private readonly baseUrl = 'http://localhost:8080/persons';

  constructor(private readonly http: HttpClient) {}

  getAll(): Observable<Person[]> {
      return this.http.get<Person[]>(this.baseUrl);
    }

   getById(id: number): Observable<Person> {
      return this.http.get<Person>(`${this.baseUrl}/${id}`);
    }

   getByColor(color: string): Observable<Person[]> {
      return this.http.get<Person[]>(`${this.baseUrl}/color/${color}`);
    }

}
