import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffortType } from 'app/shared/model/work-effort-type.model';

type EntityResponseType = HttpResponse<IWorkEffortType>;
type EntityArrayResponseType = HttpResponse<IWorkEffortType[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortTypeService {
  public resourceUrl = SERVER_API_URL + 'api/work-effort-types';

  constructor(protected http: HttpClient) {}

  create(workEffortType: IWorkEffortType): Observable<EntityResponseType> {
    return this.http.post<IWorkEffortType>(this.resourceUrl, workEffortType, { observe: 'response' });
  }

  update(workEffortType: IWorkEffortType): Observable<EntityResponseType> {
    return this.http.put<IWorkEffortType>(this.resourceUrl, workEffortType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkEffortType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkEffortType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
