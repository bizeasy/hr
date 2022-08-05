import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IParty } from 'app/shared/model/party.model';

type EntityResponseType = HttpResponse<IParty>;
type EntityArrayResponseType = HttpResponse<IParty[]>;

@Injectable({ providedIn: 'root' })
export class PartyService {
  public resourceUrl = SERVER_API_URL + 'api/parties';

  constructor(protected http: HttpClient) {}

  create(party: IParty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(party);
    return this.http
      .post<IParty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(party: IParty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(party);
    return this.http
      .put<IParty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParty>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParty[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(party: IParty): IParty {
    const copy: IParty = Object.assign({}, party, {
      birthDate: party.birthDate && party.birthDate.isValid() ? party.birthDate.format(DATE_FORMAT) : undefined,
      birthdate: party.birthdate && party.birthdate.isValid() ? party.birthdate.toJSON() : undefined,
      dateOfJoining: party.dateOfJoining && party.dateOfJoining.isValid() ? party.dateOfJoining.toJSON() : undefined,
      trainingCompletedDate:
        party.trainingCompletedDate && party.trainingCompletedDate.isValid() ? party.trainingCompletedDate.toJSON() : undefined,
      jdApprovedOn: party.jdApprovedOn && party.jdApprovedOn.isValid() ? party.jdApprovedOn.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate ? moment(res.body.birthDate) : undefined;
      res.body.birthdate = res.body.birthdate ? moment(res.body.birthdate) : undefined;
      res.body.dateOfJoining = res.body.dateOfJoining ? moment(res.body.dateOfJoining) : undefined;
      res.body.trainingCompletedDate = res.body.trainingCompletedDate ? moment(res.body.trainingCompletedDate) : undefined;
      res.body.jdApprovedOn = res.body.jdApprovedOn ? moment(res.body.jdApprovedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((party: IParty) => {
        party.birthDate = party.birthDate ? moment(party.birthDate) : undefined;
        party.birthdate = party.birthdate ? moment(party.birthdate) : undefined;
        party.dateOfJoining = party.dateOfJoining ? moment(party.dateOfJoining) : undefined;
        party.trainingCompletedDate = party.trainingCompletedDate ? moment(party.trainingCompletedDate) : undefined;
        party.jdApprovedOn = party.jdApprovedOn ? moment(party.jdApprovedOn) : undefined;
      });
    }
    return res;
  }
}
