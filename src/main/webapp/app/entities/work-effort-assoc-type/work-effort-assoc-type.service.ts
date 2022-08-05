import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';

type EntityResponseType = HttpResponse<IWorkEffortAssocType>;
type EntityArrayResponseType = HttpResponse<IWorkEffortAssocType[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortAssocTypeService {
  public resourceUrl = SERVER_API_URL + 'api/work-effort-assoc-types';

  constructor(protected http: HttpClient) {}

  create(workEffortAssocType: IWorkEffortAssocType): Observable<EntityResponseType> {
    return this.http.post<IWorkEffortAssocType>(this.resourceUrl, workEffortAssocType, { observe: 'response' });
  }

  update(workEffortAssocType: IWorkEffortAssocType): Observable<EntityResponseType> {
    return this.http.put<IWorkEffortAssocType>(this.resourceUrl, workEffortAssocType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkEffortAssocType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkEffortAssocType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
