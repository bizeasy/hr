import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITerminationType } from 'app/shared/model/termination-type.model';

type EntityResponseType = HttpResponse<ITerminationType>;
type EntityArrayResponseType = HttpResponse<ITerminationType[]>;

@Injectable({ providedIn: 'root' })
export class TerminationTypeService {
  public resourceUrl = SERVER_API_URL + 'api/termination-types';

  constructor(protected http: HttpClient) {}

  create(terminationType: ITerminationType): Observable<EntityResponseType> {
    return this.http.post<ITerminationType>(this.resourceUrl, terminationType, { observe: 'response' });
  }

  update(terminationType: ITerminationType): Observable<EntityResponseType> {
    return this.http.put<ITerminationType>(this.resourceUrl, terminationType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITerminationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITerminationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
