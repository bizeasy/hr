import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';

type EntityResponseType = HttpResponse<IWorkEffortInventoryAssign>;
type EntityArrayResponseType = HttpResponse<IWorkEffortInventoryAssign[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortInventoryAssignService {
  public resourceUrl = SERVER_API_URL + 'api/work-effort-inventory-assigns';

  constructor(protected http: HttpClient) {}

  create(workEffortInventoryAssign: IWorkEffortInventoryAssign): Observable<EntityResponseType> {
    return this.http.post<IWorkEffortInventoryAssign>(this.resourceUrl, workEffortInventoryAssign, { observe: 'response' });
  }

  update(workEffortInventoryAssign: IWorkEffortInventoryAssign): Observable<EntityResponseType> {
    return this.http.put<IWorkEffortInventoryAssign>(this.resourceUrl, workEffortInventoryAssign, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkEffortInventoryAssign>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkEffortInventoryAssign[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
