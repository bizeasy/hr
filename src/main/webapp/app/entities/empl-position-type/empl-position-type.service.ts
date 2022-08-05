import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplPositionType } from 'app/shared/model/empl-position-type.model';

type EntityResponseType = HttpResponse<IEmplPositionType>;
type EntityArrayResponseType = HttpResponse<IEmplPositionType[]>;

@Injectable({ providedIn: 'root' })
export class EmplPositionTypeService {
  public resourceUrl = SERVER_API_URL + 'api/empl-position-types';

  constructor(protected http: HttpClient) {}

  create(emplPositionType: IEmplPositionType): Observable<EntityResponseType> {
    return this.http.post<IEmplPositionType>(this.resourceUrl, emplPositionType, { observe: 'response' });
  }

  update(emplPositionType: IEmplPositionType): Observable<EntityResponseType> {
    return this.http.put<IEmplPositionType>(this.resourceUrl, emplPositionType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmplPositionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmplPositionType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
