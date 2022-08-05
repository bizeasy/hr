import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITerm } from 'app/shared/model/term.model';

type EntityResponseType = HttpResponse<ITerm>;
type EntityArrayResponseType = HttpResponse<ITerm[]>;

@Injectable({ providedIn: 'root' })
export class TermService {
  public resourceUrl = SERVER_API_URL + 'api/terms';

  constructor(protected http: HttpClient) {}

  create(term: ITerm): Observable<EntityResponseType> {
    return this.http.post<ITerm>(this.resourceUrl, term, { observe: 'response' });
  }

  update(term: ITerm): Observable<EntityResponseType> {
    return this.http.put<ITerm>(this.resourceUrl, term, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITerm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITerm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
