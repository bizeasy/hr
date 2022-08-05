import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITermType } from 'app/shared/model/term-type.model';

type EntityResponseType = HttpResponse<ITermType>;
type EntityArrayResponseType = HttpResponse<ITermType[]>;

@Injectable({ providedIn: 'root' })
export class TermTypeService {
  public resourceUrl = SERVER_API_URL + 'api/term-types';

  constructor(protected http: HttpClient) {}

  create(termType: ITermType): Observable<EntityResponseType> {
    return this.http.post<ITermType>(this.resourceUrl, termType, { observe: 'response' });
  }

  update(termType: ITermType): Observable<EntityResponseType> {
    return this.http.put<ITermType>(this.resourceUrl, termType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITermType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITermType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
