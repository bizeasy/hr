import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplPositionGroup } from 'app/shared/model/empl-position-group.model';

type EntityResponseType = HttpResponse<IEmplPositionGroup>;
type EntityArrayResponseType = HttpResponse<IEmplPositionGroup[]>;

@Injectable({ providedIn: 'root' })
export class EmplPositionGroupService {
  public resourceUrl = SERVER_API_URL + 'api/empl-position-groups';

  constructor(protected http: HttpClient) {}

  create(emplPositionGroup: IEmplPositionGroup): Observable<EntityResponseType> {
    return this.http.post<IEmplPositionGroup>(this.resourceUrl, emplPositionGroup, { observe: 'response' });
  }

  update(emplPositionGroup: IEmplPositionGroup): Observable<EntityResponseType> {
    return this.http.put<IEmplPositionGroup>(this.resourceUrl, emplPositionGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmplPositionGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmplPositionGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
