import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReasonType } from 'app/shared/model/reason-type.model';

type EntityResponseType = HttpResponse<IReasonType>;
type EntityArrayResponseType = HttpResponse<IReasonType[]>;

@Injectable({ providedIn: 'root' })
export class ReasonTypeService {
  public resourceUrl = SERVER_API_URL + 'api/reason-types';

  constructor(protected http: HttpClient) {}

  create(reasonType: IReasonType): Observable<EntityResponseType> {
    return this.http.post<IReasonType>(this.resourceUrl, reasonType, { observe: 'response' });
  }

  update(reasonType: IReasonType): Observable<EntityResponseType> {
    return this.http.put<IReasonType>(this.resourceUrl, reasonType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReasonType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReasonType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
