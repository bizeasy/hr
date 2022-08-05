import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILocationType } from 'app/shared/model/location-type.model';

type EntityResponseType = HttpResponse<ILocationType>;
type EntityArrayResponseType = HttpResponse<ILocationType[]>;

@Injectable({ providedIn: 'root' })
export class LocationTypeService {
  public resourceUrl = SERVER_API_URL + 'api/location-types';

  constructor(protected http: HttpClient) {}

  create(locationType: ILocationType): Observable<EntityResponseType> {
    return this.http.post<ILocationType>(this.resourceUrl, locationType, { observe: 'response' });
  }

  update(locationType: ILocationType): Observable<EntityResponseType> {
    return this.http.put<ILocationType>(this.resourceUrl, locationType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
