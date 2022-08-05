import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmplPosition } from 'app/shared/model/empl-position.model';

type EntityResponseType = HttpResponse<IEmplPosition>;
type EntityArrayResponseType = HttpResponse<IEmplPosition[]>;

@Injectable({ providedIn: 'root' })
export class EmplPositionService {
  public resourceUrl = SERVER_API_URL + 'api/empl-positions';

  constructor(protected http: HttpClient) {}

  create(emplPosition: IEmplPosition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplPosition);
    return this.http
      .post<IEmplPosition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emplPosition: IEmplPosition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emplPosition);
    return this.http
      .put<IEmplPosition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmplPosition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmplPosition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(emplPosition: IEmplPosition): IEmplPosition {
    const copy: IEmplPosition = Object.assign({}, emplPosition, {
      estimatedFromDate:
        emplPosition.estimatedFromDate && emplPosition.estimatedFromDate.isValid()
          ? emplPosition.estimatedFromDate.format(DATE_FORMAT)
          : undefined,
      estimatedThruDate:
        emplPosition.estimatedThruDate && emplPosition.estimatedThruDate.isValid()
          ? emplPosition.estimatedThruDate.format(DATE_FORMAT)
          : undefined,
      actualFromDate:
        emplPosition.actualFromDate && emplPosition.actualFromDate.isValid() ? emplPosition.actualFromDate.format(DATE_FORMAT) : undefined,
      actualThruDate:
        emplPosition.actualThruDate && emplPosition.actualThruDate.isValid() ? emplPosition.actualThruDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.estimatedFromDate = res.body.estimatedFromDate ? moment(res.body.estimatedFromDate) : undefined;
      res.body.estimatedThruDate = res.body.estimatedThruDate ? moment(res.body.estimatedThruDate) : undefined;
      res.body.actualFromDate = res.body.actualFromDate ? moment(res.body.actualFromDate) : undefined;
      res.body.actualThruDate = res.body.actualThruDate ? moment(res.body.actualThruDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((emplPosition: IEmplPosition) => {
        emplPosition.estimatedFromDate = emplPosition.estimatedFromDate ? moment(emplPosition.estimatedFromDate) : undefined;
        emplPosition.estimatedThruDate = emplPosition.estimatedThruDate ? moment(emplPosition.estimatedThruDate) : undefined;
        emplPosition.actualFromDate = emplPosition.actualFromDate ? moment(emplPosition.actualFromDate) : undefined;
        emplPosition.actualThruDate = emplPosition.actualThruDate ? moment(emplPosition.actualThruDate) : undefined;
      });
    }
    return res;
  }
}
