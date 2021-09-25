import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:8080/api/v1/loans';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor(private httpClient: HttpClient) { }

  read(id: string): Observable<any> {
    return this.httpClient.get(`${BASE_URL}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.httpClient.post(BASE_URL, data);
  }
}
